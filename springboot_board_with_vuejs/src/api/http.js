import axios from 'axios';
import store from '../store';
import {setTokenInLocalStorage,setAcessTokenInLocalStorage,setRefreshTokenInLocalStorage} from "../store/token"
import {setSnackBarInfo} from "../api/common_api"
const instance = axios.create({
	baseURL : "http://ec2-13-125-170-210.ap-northeast-2.compute.amazonaws.com:8080/",
	//baseURL : "http://localhost:8080",
	//withCredentials: true,				// 설정해야 쿠키가 Request에 저장되어 세션이 유지됨
});


/*
    모든 요청 전 header에 access_token을 담아 전송한다.
 */
instance.interceptors.request.use(
    config => {
		let jwtToken = localStorage.getItem('access_token');
	
        if ((jwtToken !== null) && (jwtToken !== "")) {
			
			config.headers.Authorization = "bearer " + jwtToken;
			//config.headers.common['X-AUTH-TOKEN'] = jwtToken;
        }
         console.log('Interceptors Request is', config, new Date());
        return config
    },
    error => {
         console.log('Interceptors Request Error is', error.response, new Date());
        return Promise.reject(error);
    }
);


instance.interceptors.response.use(
   response => {
	    console.log('Interceptors Response is ', response, new Date());
	   store.state.common.loading = false;
		return response;
   },
   function (error) {
	    console.log('Interceptors Response Error is ', error.response, new Date());
	   store.state.common.loading = false;

	   if (!error.response) {
		
		   store.commit('NETWORK_ERROR')
		   return Promise.reject(error);
	   }

	   let status = error.response.status;
	   if (status === 404) {
		   store.commit('PUSH_ERROR_PAGE');
		   return Promise.reject(error);
	   }

	   if( status === 400){
			store.commit('OPEN_MODAL', {title: '에러', content: "입력이 올바르지 않습니다", option1: '닫기',});
	   }
	   if (error.config && error.response && status === 401) {
		   let errorData = error.response.data.message;
		 

		   if (errorData === "토큰 유효기간이 지났습니다") {
			   return attemptRefreshToken().then(()=>{
				error.config.headers.Authorization = "bearer " + localStorage.getItem('access_token');
				return instance.request(error.config);
			});
		   } else {
		
			   store.commit('OPEN_SNACKBAR', setSnackBarInfo('토큰 정보가 잘못되었습니다. 다시 로그인 해주세요', 'error', 'top'));
			   localStorage.removeItem("access_token");
			   localStorage.removeItem("refresh_token");
			}
	   }

	   if(status === 403){
			store.commit('OPEN_SNACKBAR', setSnackBarInfo("권한이 없습니다", 'error', 'top'))
	   }

	   if(status === 500){
		   store.commit('OPEN_MODAL', {title: '실패', content: error.response.data.message, option1: '닫기',});
	   }
	   return Promise.reject(error);
   }
);


function requestRefreshToken() {
    let form = new FormData();
    form.append("grant_type", "refresh_token");
    form.append('refresh_token', localStorage.getItem('refresh_token'));
    const requestData = {
        url: `${process.env.VUE_APP_BASEURL}/oauth/token`,
        method: "POST",
        auth: {
            username: process.env.VUE_APP_CLIENTID,
            password: process.env.VUE_APP_CLIENTSECRET,
        },
        data: form
    };
    return instance(requestData);
}

function attemptRefreshToken() {
    store.commit('OPEN_SNACKBAR', setSnackBarInfo('토큰이 만료되어 재발급 합니다.', 'error', 'top'));
    return requestRefreshToken().then(res => {
        setAcessTokenInLocalStorage(res.data.access_token);
        store.commit('OPEN_SNACKBAR', setSnackBarInfo('토큰이 재발급 되었습니다.', 'info', 'top')
        )
    })
        .catch(() => {
            store.commit('OPEN_SNACKBAR', setSnackBarInfo('Refresh Token이 만료되었습니다. 다시 한번 로그인해주세요.', 'error', 'top'))
            store.commit('LOGOUT');
        });
}

export default instance;