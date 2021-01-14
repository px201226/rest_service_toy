import router from "../router";

const state = {

    appbarShow: true,
    navibarShow: true,
    loading: false,
    commentModal: false,
    postModal: false,
    errorMessage: "",
    
    modal: {
        open: false,
        title: '',
        content: '',
        option1: '',
        option2: '',
        event: '',
        data:{},
    },
    snackbar: {
        open: false,
        text: '',
        location: 'top'
    },

    // ============ Rules ============
    emailRules: [
        v => !!v || '이메일을 작성해주세요.',
        v => /.+@.+\..+/.test(v) || '이메일 형식으로 작성해주세요.',
        v => v.search(/\s/) === -1 || '공백을 제거해주세요!'
    ],
    nameRules: [
        v => !!v || '이름을 작성해주세요.',
        v => v.search(/\s/) === -1 || '공백을 제거해주세요!'
    ],
    passwordRules: [
        v => !!v || '비밀번호를 작성해주세요.',
        v => (v.length >= 2) || '2글자 이상 작성해주세요!',
        v => v.search(/\s/) === -1 || '공백을 제거해주세요!'
    ],
    typeRules: [v => !!v || '항목을 선택해주세요.']
};

const getters = {
    GET_LOADING_STATE(state) {
        return state.loading;
    },

    IS_APPBAR_SHOW(state) {
        return state.appbarShow;
    },

    IS_NAVIBAR_SHOW(state) {
        return state.navibarShow;
    },
      
};

const mutations = {
    APPBAR_DISPLAY(state, display) {
        state.appbarShow = display;
    },
    NAVIBAR_DISPLAY(state, display) {
        state.navibarShow = display;
    },

    CLOSE_MODAL(state) {
        state.modal.open = false;
    },
    OPEN_MODAL(state, modalTexts) {
        state.loading = false;
        state.modal.title = modalTexts.title;
        state.modal.content = modalTexts.content;
        state.modal.option1 = modalTexts.option1;
        state.modal.option2 = modalTexts.option2
            ? modalTexts.option2
            : null;
        state.modal.data = modalTexts.data;
        state.modal.event = modalTexts.event ? modalTexts.event : "pass";
        state.modal.open = true;
    },
    NETWORK_ERROR(state) {
        state.loading = false;
        state.modal.title = "네트워크 연결 에러"
        state.modal.content = "서버와 연결할 수 없습니다."
        state.modal.option1 = "닫기";
        state.modal.option2 = null
        state.modal.event = "pass"
        state.modal.open = true;
    },
    OPEN_SNACKBAR(state, snackbarInfo) {
        state.snackbar.open = true;
        state.snackbar.text = snackbarInfo.text;
        state.snackbar.color = snackbarInfo.color;
        state.snackbar.location = snackbarInfo.location;
    },
    START_LOADING(state) {
        state.loading = true;
    },
    END_LOADING(state) {
        state.loading = false;
    },

    PUSH_ERROR_PAGE(state) {
        state.errorMessage = '잘못된 요청입니다.';
        router.push('/error');
    },

    OPEN_COMMENTS(state){
        state.commentModal = true;
    },
    
    CLOSE_COMMENTS(state){
        state.commentModal = false;
    },

    OPEN_POST_WRITER(state){
        state.postModal = true;
    },
    
    CLOSE_POST_WRITER(state){
        state.postModal = false;
    },  
    
};

const actions = {
     
};


// const setTokenInLocalStorage = (jwtToken) => {
//     localStorage.setItem("jwtToken", jwtToken);
//   };

  

export default {mutations, state, actions, getters};