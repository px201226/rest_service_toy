const setTokenInLocalStorage = (jwtToken) => {
    localStorage.setItem("jwtToken", jwtToken);
  };

  
  
export {setTokenInLocalStorage};