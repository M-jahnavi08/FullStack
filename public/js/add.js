function addProduct(){

const name = document.getElementById("name").value;
const category = document.getElementById("category").value;
const price = document.getElementById("price").value;
const quantity = document.getElementById("quantity").value;

fetch("/api/products",{
method:"POST",
headers:{"Content-Type":"application/json"},
body:JSON.stringify({name,category,price,quantity})
})
.then(()=>{
alert("Product Added");
window.location.href="products.html";
});

}