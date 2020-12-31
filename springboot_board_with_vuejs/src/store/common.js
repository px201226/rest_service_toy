import router from "../router";

const state = {

    modal: {open: false, title: '', content: '', option1: '', option2: '',},
    snackbar: {open: false, text: '', location: 'top',},

    // ============ Rules ============ //
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
        v => v.search(/\s/) === -1 || '공백을 제거해주세요!',
      
    ],
    typeRules: [
        v => !!v || '항목을 선택해주세요.',
    ],

};

const getters = {};

const mutations = {
     CLOSE_MODAL(state) {
        state.modal.open = false;
    },
    OPEN_MODAL(state, modalTexts) {
        state.loadingState = false;
        state.modal.title = modalTexts.title;
        state.modal.content = modalTexts.content;
        state.modal.option1 = modalTexts.option1;
        state.modal.option2 = modalTexts.option2 ? modalTexts.option2 : null;
        state.modal.open = true;
    },
    OPEN_SNACKBAR(state, snackbarInfo) {
        state.snackbar.open = true;
        state.snackbar.text = snackbarInfo.text;
        state.snackbar.color = snackbarInfo.color;
        state.snackbar.location = snackbarInfo.location;
    },
};

const actions = {};

export default {mutations, state, actions, getters};