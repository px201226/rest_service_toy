import context from '../store';

function setSnackBarInfo(text, color, location) {
    return {
        text: text,
        color: color,
        location: location,
    }
  }
  

function validateLogin() {
    if (!context.getters.GET_IS_LOGIN) {
      context.commit("OPEN_SNACKBAR", {
        text: "로그인이 필요합니다",
        color: "error",
        location: "top",
      });
      return false;
    }
    return true;
}

export { validateLogin , setSnackBarInfo}