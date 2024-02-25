const orderCancelBtns = document.querySelectorAll(".order__cancel__btn");

const orderIds = document.querySelectorAll(".order__id");

function orderCancel(orderId, msg) {
  if (confirm(msg)) {
    const form = document.createElement("form");
    form.method = "post";
    form.action = "/orders/cancel"; // 취소 처리를 담당하는 컨트롤러의 엔드포인트로 설정

    const orderIdInput = document.createElement("input");
    orderIdInput.type = "hidden";
    orderIdInput.name = "orderId";
    orderIdInput.value = orderId;
    form.appendChild(orderIdInput);

    document.body.appendChild(form);
    form.submit();
  }
}
