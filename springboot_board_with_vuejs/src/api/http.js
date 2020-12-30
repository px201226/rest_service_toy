import axios from 'axios';

const instance = axios.create({
	baseURL : "http://ec2-13-125-170-210.ap-northeast-2.compute.amazonaws.com",
	//baseURL : "http://localhost:8080",
	withCredentials: true,				// 설정해야 쿠키가 Request에 저장되어 세션이 유지됨
});

export default instance;