let allProducts = [];

const container = document.getElementById("productsContainer");

/* LOAD PRODUCTS */

function loadProducts(){

fetch("/api/products")
.then(res => res.json())
.then(data => {

allProducts = data;
displayProducts(data);

/* LOW STOCK ALERT */
let low = data.filter(p => p.quantity < 5);
if(low.length > 0){
alert("⚠ Low stock items: " + low.length);
}

});

}

/* DISPLAY PRODUCTS */

function displayProducts(data){

container.innerHTML = "";

data.forEach(p => {

const div = document.createElement("div");
div.className = "card";

/* STATUS */
let status = p.quantity == 0 
? "<p class='out'>Out of Stock</p>" 
: "<p class='in'>In Stock</p>";

div.innerHTML = `
<h3>${p.name}</h3>
<p>${p.category}</p>
<p>₹${p.price}</p>
<p>Qty: ${p.quantity}</p>
${status}

<button onclick="editProduct(${p.id},'${p.name}','${p.category}',${p.price},${p.quantity})">Edit</button>
<button onclick="deleteProduct(${p.id})">Delete</button>
`;

container.appendChild(div);

});

}

/* ADD PRODUCT */

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
loadProducts();
clearForm();
});

}

/* DELETE PRODUCT */

function deleteProduct(id){

fetch(`/api/products/${id}`,{method:"DELETE"})
.then(()=>loadProducts());

}

/* EDIT PRODUCT */

function editProduct(id,name,category,price,quantity){

document.getElementById("name").value = name;
document.getElementById("category").value = category;
document.getElementById("price").value = price;
document.getElementById("quantity").value = quantity;

const btn = document.querySelector("button");
btn.innerText = "Update Product";

btn.onclick = function(){

fetch(`/api/products/${id}`,{
method:"PUT",
headers:{"Content-Type":"application/json"},
body:JSON.stringify({
name:document.getElementById("name").value,
category:document.getElementById("category").value,
price:document.getElementById("price").value,
quantity:document.getElementById("quantity").value
})
})
.then(()=>{
loadProducts();
clearForm();
btn.innerText="Add Product";
btn.onclick=addProduct;
});

};

}

/* SEARCH */

function searchProducts(){

const search = document.getElementById("search").value.toLowerCase();

const filtered = allProducts.filter(p =>
p.name.toLowerCase().includes(search) ||
p.category.toLowerCase().includes(search)
);

displayProducts(filtered);

}

/* FILTER FUNCTIONS */

function showAll(){
displayProducts(allProducts);
}

function showInStock(){
displayProducts(allProducts.filter(p => p.quantity > 0));
}

function showOutStock(){
displayProducts(allProducts.filter(p => p.quantity == 0));
}

/* CLEAR FORM */

function clearForm(){
document.getElementById("name").value="";
document.getElementById("category").value="";
document.getElementById("price").value="";
document.getElementById("quantity").value="";
}

/* LOAD ON START */

loadProducts();