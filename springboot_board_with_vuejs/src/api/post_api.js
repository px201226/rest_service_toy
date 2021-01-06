import http from './http';

export async function getPostsList(page){
	return http.get("/v1/posts?page=" + page + "&size=5&sort=id,desc");
};

export async function getPost(postId){
	return http.get("/v1/posts/"+postId);
};

export async function writePost(req){
	return http.post("/v1/posts", req)
};

export async function updatePost(postId, req){
	return http.put("/v1/posts/" + postId,req)
};

export async function deletePost(postId, ser){
	return http.delete("/v1/posts/" + postId)
};