const homeMenuBtns = document.querySelectorAll(".home__menu__btn");

function activateButton(button) {
  homeMenuBtns.forEach(function (homeMenuBtn) {
    homeMenuBtn.classList.remove("active");
  });

  button.classList.add("active");
}

document.addEventListener("DOMContentLoaded", function () {
  const urlParams = new URLSearchParams(window.location.search);
  const orderBy = urlParams.get("orderBy");

  // orderBy 매개변수가 'views'일 때 클래스 추가
  if (orderBy === "views") {
    homeMenuBtns.forEach(function (homeMenuBtn) {
      homeMenuBtn.classList.remove("active");
    });
    const orderByViews = document.querySelector(".orderby__views");
    orderByViews.classList.add("active"); // 여기서 'yourClassName'은 추가할 클래스명입니다.
  }
});
