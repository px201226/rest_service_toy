const setTokenInLocalStorage = (jwtToken) => {
    localStorage.setItem("jwtToken", jwtToken);
  };

  
  function setSnackBarInfo(text, color, location) {
    return {
        text: text,
        color: color,
        location: location,
    }
  }
  
export {setTokenInLocalStorage,setSnackBarInfo};