import http from './http';


export async function join(req){
	return http.post("/v1/join", req);
};


export async function getOAuthToken(user) {
    let form = new FormData();
    user.email = user.email.toLowerCase();
    form.append('username', user.email);
    form.append('password', user.password);
    form.append("grant_type", "password");
    const requestData = {
        url: `/oauth/token`,
        method: "POST",
        auth: {
            username: process.env.VUE_APP_CLIENTID,
            password: process.env.VUE_APP_CLIENTSECRET,
        },
        data: form
    };
    return http(requestData);
}

export async function getUser(){
	return http.get("/v1/user");
};

export async function getPostList(){
	return http.get("/v1/user/posts");
};

export async function getCommentList(){
	return http.get("/v1/user/comments");
};


