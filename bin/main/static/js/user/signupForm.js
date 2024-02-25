'use strict';

const emailSendBtn = document.querySelector('.email__send__btn');
const emailUpdateBtn = document.querySelector('.email__update__btn');
const emailCancelBtn = document.querySelector('.email__cancel__btn');
const emailVerifyButton = document.querySelector('.email__verify__btn');
const userEmail = document.getElementById('userEmail');
const userEmailBefore = document.getElementById('userEmailBefore');
const emailCode = document.getElementById('emailCode');

userEmail.addEventListener('keyup', () => {
    emailSendBtn.disabled = isNull(userEmail.value.toString());
})
emailSendBtn.addEventListener('click', () => {

    if (isNull(userEmail.value.toString())) {
        emailSendBtn.disabled = true
    } else {
        userEmail.disabled = true;
        emailCode.value = '';
        emailCode.disabled = false;
        emailVerifyButton.disabled = false;
        emailUpdateBtn.style.display = 'inline-block';
        emailSendBtn.style.display = 'none';
        // fetch 함수에 URL 전달
        fetch(
            "/emails/sendEmail", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    sendTo: userEmail.value,
                    emailType: "SIGNUPAUTH"
                })
            })
            .then(function (response) {
                //return response.json();
            })
            // .then(function (data) {
            //     console.log(data); // JSON 데이터를 출력
            // })
            .catch(function (error) {
                console.error(error); // 에러를 출력
            });
    }
});

emailUpdateBtn.addEventListener('click', () => {
    emailSendBtn.style.display = 'inline-block';
    emailUpdateBtn.style.display = 'none';
    emailCancelBtn.style.display = 'inline-block';
    userEmailBefore.value = userEmail.value;
    userEmail.disabled = false;
    emailCode.disabled = true;
    emailCode.value = "";
    emailVerifyButton.disabled = true;
});

emailCancelBtn.addEventListener('click', () => {
    emailSendBtn.style.display = 'none';
    emailUpdateBtn.style.display = 'inline-block';
    emailCancelBtn.style.display = 'none';
    userEmail.value = userEmailBefore.value;
    userEmail.disabled = true;
    emailVerifyButton.disabled = false;
    emailSendBtn.disabled = true;
    emailCode.disabled = false;
    emailVerifyButton.disabled = false;
});


emailVerifyButton.addEventListener('click', () => {

    fetch("/users/checkEmailCode", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            userEmail: userEmail.value,
            emailCode: emailCode.value
        })
    })
        .then(function (response) {
            return response.json(); // 응답을 JSON 형식으로 변환
        })
        .then(function (data) {
            console.log(data);
            if (data) {
                const success = document.getElementById('email__verify__success');
                alert(success.value);
                emailCode.disabled = true;
                emailVerifyButton.disabled = true;
            } else {
                const fail = document.getElementById('email__verify__fail');
                alert(fail.value);
            }

        })
        .catch(function (error) {
            console.error(error); // 에러를 출력
        });
});


function signup(){
    userEmail.disabled = false;
    emailCode.disabled = false;
}

