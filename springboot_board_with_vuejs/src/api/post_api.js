import http from './http';

export async function updatePostsList(){
	return http.get("/api/v1/posts");
};

export async function readPostByPostId(postId){
	return http.get("/api/v1/posts/" + postId)
};

export async function writePost(postSaveReqeustDto){
	return http.post("/api/v1/posts/" , postSaveReqeustDto)
};

export async function updatePost(postid, postUpdateRequestDto){
	return http.put("/api/v1/posts/" + postid, postUpdateRequestDto)
};

export async function deletePost(postId, user){
	return http.delete("/api/v1/posts/" + postId)
};