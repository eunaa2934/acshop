"use strict";

window.isNull = function (str) {
  //export function isNull(str){
  return (
    typeof str == "undefined" ||
    str == null ||
    str === "" ||
    str.trim().length === 0
  );
};
