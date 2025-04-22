export const API_CONFIG = {
  BASE_URL: 'http://localhost:7278/',
  ENDPOINTS: {
    AUTH: {
      LOGIN: 'auth/login',
      REGISTER: 'auth/register',
      CHECK: 'auth/check',
      REFRESH: 'auth/refresh',
    },
    GLOBALS: {
      BASE_URL: 'globals/',
      GET_PROFESSIONALS: 'globals/get-professionals',
      ACCEPT_PROBLEM: 'globals/accept-problem',
      REFUSE_PROBLEM: 'globals/refuse-problem',
      CHANGE_PRIORITY: 'globals/change-priority',
      GET_PROBLEMS: 'globals/get-problems',
      GET_SUPPORT_CENTER: 'globals/get-center',
    },
    CLIENT: {
      BASE_URL: 'client/',
      MAKE_APPLICATION: 'client/make-application',
      GET_CONCLUSION: 'client/conclusion',
      ACCEPT_CONCLUSION: 'client/accept-conclusion',
      REFUSE_CONCLUSION: 'client/refuse-conclusion',
      RATE: 'client/rate/',
    },
    PROFESSIONAL: {
      BASE_URL: 'professional/',
      MAKE_JUDGMENT: 'professional/make-judgment',
      GET_USER: 'professional/get-user',
    },
    SUPPORT_CENTER: {
      BASE_URL: 'support-center/',
      SET_PROFESSIONAL: 'support-center/set-professional',
      CLOSE_PROBLEM: 'support-center/close-problem',
    },
    ADMIN: {
      BASE_URL: 'admin/',
      GET_USERS: 'admin/get-users',
      ADD_SUPPORT_CENTER: 'admin/add-support-center',
    },
  },
};
