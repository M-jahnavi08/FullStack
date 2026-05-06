fetch("/api/stats")
.then(res => res.json())
.then(data => {

document.getElementById("totalProducts").innerText = data.totalProducts || 0;
document.getElementById("totalStock").innerText = data.totalStock || 0;
document.getElementById("lowStock").innerText = data.lowStock || 0;

});