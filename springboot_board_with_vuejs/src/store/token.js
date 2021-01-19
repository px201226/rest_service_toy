const setTokenInLocalStorage = (token) => {
  localStorage.setItem("access_token", token.access_token);
  localStorage.setItem("refresh_token", token.refresh_token);
  };

const setAcessTokenInLocalStorage = (token) => {
  localStorage.setItem("access_token", token)
};

const setRefreshTokenInLocalStorage = (token) => {
  localStorage.setItem("refresh_token", token)
};
  
const deleteToken = () => {
  localStorage.removeItem("access_token");
  localStorage.removeItem("refresh_token");
  localStorage.removeItem("user");
};
export {setTokenInLocalStorage,setAcessTokenInLocalStorage,setRefreshTokenInLocalStorage,deleteToken};