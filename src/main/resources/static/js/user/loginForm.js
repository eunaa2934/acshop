"use strict";
// URL에서 error 파라미터를 가져와서 실패 메시지를 표시합니다.
const urlParams = new URLSearchParams(window.location.search);
const error = urlParams.get("error");
if (error === "true") {
  alert("로그인에 실패했습니다. 올바른 이메일과 비밀번호를 입력하세요.");
}
