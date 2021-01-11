import axios from 'axios';
import store from '../store';
import {setTokenInLocalStorage,setSnackBarInfo} from "../store/token"

const instance = axios.create({
	//baseURL : "http://ec2-13-125-170-210.ap-northeast-2.compute.amazonaws.com",
	baseURL : "http://localhost:8080",
	withCredentials: true,				// 설정해야 쿠키가 Request에 저장되어 세션이 유지됨
});


/*
    모든 요청 전 header에 access_token을 담아 전송한다.
 */
instance.interceptors.request.use(
    config => {
		let jwtToken = store.getters.GET_JWT_TOKEN; 
		localStorage.getItem('jwtToken');
        if (jwtToken !== null) {
			config.headers.common['X-AUTH-TOKEN'] = jwtToken;
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
	   if (status === 401) {
		   let errorData = error.response.data;
		   if (errorData.error !== 'invalid_token') {
			   return Promise.reject(error);
		   }

		   if (isExpiredToken(errorData)) {
			   return attemptRefreshToken();
		   } else {
			   store.commit('LOGOUT_WITH_TOKEN_INVALIDE');
			   store.commit('SET_SNACKBAR', setSnackBarInfo('토큰 정보가 잘못되었습니다. 다시 로그인 해주세요', 'error', 'top'));
		   }
	   }

	   if(status === 403){
			store.commit('OPEN_SNACKBAR', setSnackBarInfo("권한이 없습니다", 'error', 'top'))
	   }
	   return Promise.reject(error);
   }
);


export default instance;