function fillShippingAddress() {
    const billingCountry = document.querySelector(".address #country");
    const shippingCountry = document.querySelector(".address #s-country");
    billingCountry.addEventListener("input", (event) => changeValue(billingCountry, shippingCountry));

    const billingZipcode = document.querySelector(".address #zipcode");
    const shippingZipcode = document.querySelector(".address #s-zipcode");
    billingZipcode.addEventListener("input", (event) => changeValue(billingZipcode, shippingZipcode));

    const billingCity = document.querySelector(".address #city");
    const shippingCity = document.querySelector(".address #s-city");
    billingCity.addEventListener("input", (event) => changeValue(billingCity, shippingCity));

    const billingStreet = document.querySelector(".address #street");
    const shippingStreet = document.querySelector(".address #s-street");
    billingStreet.addEventListener("input", (event) => changeValue(billingStreet, shippingStreet));

    const billingDoorNumber = document.querySelector(".address #number");
    const shippingDoorNumber = document.querySelector(".address #s-number");
    billingDoorNumber.addEventListener("input", (event) => changeValue(billingDoorNumber, shippingDoorNumber));
}

function changeValue(node1, node2) {
    node2.value = node1.value;
}

fillShippingAddress();