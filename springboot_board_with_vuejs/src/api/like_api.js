import http from './http';

export async function like(postId){
	return http.post("/v1/like/"+postId);
};

export async function unlike(postId){
	return http.delete("/v1/like/"+postId);
};
