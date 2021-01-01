import axios from 'axios';
import store from '../store';

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
		console.log("interceptor")
		console.log(jwtToken);
		localStorage.getItem('jwtToken');
        if (jwtToken !== null) {
			config.headers.common['X-AUTH-TOKEN'] = jwtToken;
			console.log("헤더설정")
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
	 
		return response;
   },
   function (error) {
	    console.log('Interceptors Response Error is ', error.response, new Date());

	   if (!error.response) {
		
		   store.commit('NETWORK_ERROR')
		   return Promise.reject(error);
	   }

	   let status = error.response.status;
	   if (status === 500) {
		   store.commit('PUSH_ERROR_PAGE');
		   return Promise.reject(error);
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

	   return Promise.reject(error);
   }
);


export default instance;