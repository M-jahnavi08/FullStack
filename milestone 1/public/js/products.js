let allProducts = [];
const container = document.getElementById("productsContainer");

function loadProducts(){
fetch("/api/products")
.then(res=>res.json())
.then(data=>{
allProducts = data;
display(data);
});
}

function display(data){
container.innerHTML="";

data.forEach(p=>{
container.innerHTML += `
<div class="card">
<h3>${p.name}</h3>
<p>${p.category}</p>
<p>Qty: ${p.quantity}</p>
</div>
`;
});
}

function searchProducts(){
const val = document.getElementById("search").value.toLowerCase();
display(allProducts.filter(p =>
p.name.toLowerCase().includes(val)
));
}

loadProducts();