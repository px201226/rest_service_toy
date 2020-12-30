import http from './http';

export async function getSessionUser(){
	return http.get("/api/v1/user");
};

export async function logout(){
	return http.post("/logout");
};


export async function getTestUser(){
	return http.get("/testLogin");
};

