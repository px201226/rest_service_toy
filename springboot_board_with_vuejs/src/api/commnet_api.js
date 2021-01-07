import http from './http';

export async function getCommentList(postId){
	return http.get("/v1/posts/" + postId + "/comments");
};

export async function writeComment(postId,req){
	return http.post("/v1/posts/" + postId + "/comments", req)
};

export async function updateComment(postId,commentId, req){
	return http.put("/v1/posts/" + postId + "/comments/" + commentId ,req)
};

export async function deleteComment(postId,commentId){
	return http.delete("/v1/posts/" + postId + "/comments/" + commentId)
};