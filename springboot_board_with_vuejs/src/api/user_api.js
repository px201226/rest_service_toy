import http from './http';


export async function join(req){
	return http.post("/v1/join", req);
};

export async function login(req){
	return http.post("/v1/login", req);
};

export async function getUser(){
	return http.get("/v1/user");
};

