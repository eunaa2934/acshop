'use strict';

let productPictureActive;
const productPictures = document.querySelectorAll('.product__pictures');
const orderOptions = document.querySelector('.order__options tbody');
document.addEventListener('DOMContentLoaded', () => {
    orderOptions.children[0].classList.add('active');
    productPictureActive = document.querySelector('.product__pictures.opt0');
    productPictureActive.classList.add('active');
});


for (let i = 0; i < orderOptions.rows.length; i++) {
    orderOptions.rows[i].addEventListener('click', (event) => {

        let optionBefore = document.querySelector('.order__option.active');
        optionBefore.classList.remove('active');

        let optionNum = event.currentTarget.rowIndex - 1;
        orderOptions.children[optionNum].classList.add('active');

        // 현재 활성화된 product picture 비활성화
        productPictures.forEach(item => {
            item.classList.remove('active');
        });
        // 새로운 활성화된 product picture 찾기
        productPictureActive = document.querySelector('.product__pictures.opt' + optionNum);
        productPictureActive.classList.add('active');
    })
}


let optionQuantity = document.querySelectorAll('.order__option__optionQuantity>select');
const orderTotalPrice = document.querySelector('.order__total__price');

const orderBtn = document.querySelector('.order__btn');
optionQuantity.forEach(option => {
    option.addEventListener('change', event => {
        let sum = 0;
        optionQuantity.forEach((selectedQuantity, index) => {
            let optionPrice = document.querySelectorAll('.order__option__optionPrice>input');
            sum += optionPrice[index].value * selectedQuantity.value;
            orderTotalPrice.innerHTML = sum;
        })
        if(sum>0){
            orderBtn.disabled = false;
        }else{
            orderBtn.disabled = true;
        }
    })
})
