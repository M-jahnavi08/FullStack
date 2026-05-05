const express = require('express');
const mongoose = require('mongoose');
require('dotenv').config(); // This looks for your .env file

const app = express();

// Use the variable from .env
const uri = process.env.MONGO_URI;

mongoose.connect(uri)
  .then(() => console.log("✅ Vastra3D Database Connected Successfully!"))
  .catch(err => console.error("❌ Connection Error:", err));

app.listen(5000, () => console.log("🚀 Server running on port 5000"));