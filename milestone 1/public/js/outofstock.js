const container = document.getElementById("lowContainer");

fetch("/api/products")
.then(res=>res.json())
.then(data=>{

const low = data.filter(p => p.quantity < 10);

low.forEach(p=>{
container.innerHTML += `
<div class="card">
<h3>${p.name}</h3>
<p>Qty: ${p.quantity}</p>
<p style="color:red">Low Stock</p>
</div>
`;
});

});