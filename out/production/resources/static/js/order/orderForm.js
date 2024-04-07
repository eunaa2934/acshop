let optionQuantity = document.querySelectorAll(
  ".order__option__optionQuantity>select",
);
const orderTotalPrice = document.querySelector(".order__total__price");

document.addEventListener("DOMContentLoaded", () => {
  let sum = 0;
  optionQuantity.forEach((selectedQuantity, index) => {
    let optionPrice = document.querySelectorAll(
      ".order__option__optionPrice>input",
    );
    sum += optionPrice[index].value * selectedQuantity.value;
    orderTotalPrice.innerHTML = sum;
  });
});

optionQuantity.forEach((option) => {
  option.addEventListener("change", (event) => {
    showSum();
  });
});

function deleteOrderOption(rowStat1, rowStat2) {
  // 해당 옵션 수량 0으로 변경
  let deleteOrderProductOptionQuantity = document.getElementById(
    "orderProducts" +
      rowStat1 +
      ".orderProductOptions" +
      rowStat2 +
      ".orderOptionQuantity",
  );
  for (let i = 0; i < deleteOrderProductOptionQuantity.options.length; i++) {
    deleteOrderProductOptionQuantity.options[i].selected =
      deleteOrderProductOptionQuantity.options[i].value === "0";
  }

  let deleteOrderProductOption = document.querySelector(
    ".orderProducts" + rowStat1 + ".orderProductOptions" + rowStat2,
  );
  showSum();
  // 해당 옵션 display:none으로 변경
  deleteOrderProductOption.style.display = "none";
}

function showSum() {
  let sum = 0;
  optionQuantity.forEach((selectedQuantity, index) => {
    let optionPrice = document.querySelectorAll(
      ".order__option__optionPrice>input",
    );
    sum += optionPrice[index].value * selectedQuantity.value;
    orderTotalPrice.innerHTML = sum;
  });
}
