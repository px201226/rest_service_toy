import http from './http';

export async function getNextMatchingDay(){
	return http.get("/v1/matching/nextDay");
};

export async function applyMatching(){
	return http.post("/v1/matching/apply");
};

export async function getApplyHistory(){
	return http.get("/v1/matching/apply");
};

export async function getMatchingResult(){
	return http.get("/v1/matching/result");
};