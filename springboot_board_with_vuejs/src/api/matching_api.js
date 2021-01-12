import http from './http';

export async function getNextMatchingDay(){
	return http.get("/v1/matching/nextDay");
};
