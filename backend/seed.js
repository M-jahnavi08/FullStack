const mongoose = require('mongoose');
require('dotenv').config();
const Product = require('./models/Product');

const sampleProducts = [
  {
    title: "Royal Gold Silk Sherwani",
    description: "Hand-embroidered premium silk for grand weddings.",
    category: "Wedding",
    imageUrl: "https://images.unsplash.com/photo-1597983073493-88cd35cf93b0?w=800",
    glbPath: "/models/avatar.glb",
    affiliateLinks: [
      { platform: "Myntra", url: "https://www.myntra.com/men-sherwani", price: 15999 },
      { platform: "Ajio", url: "https://www.ajio.com/men-wedding-wear", price: 14500 }
    ]
  },
  {
    title: "Sun-Kissed Haldi Kurta",
    description: "Lightweight mustard cotton with mirror work.",
    category: "Haldi",
    imageUrl: "https://images.unsplash.com/photo-1595910414916-721209304381?w=800",
    glbPath: "/models/avatar.glb",
    affiliateLinks: [
      { platform: "Myntra", url: "https://www.myntra.com/yellow-kurta", price: 2499 }
    ]
  },
  {
    title: "Midnight Sequin Evening Gown",
    description: "Perfect for cocktail parties and receptions.",
    category: "Parties",
    imageUrl: "https://images.unsplash.com/photo-1496747611176-843222e1e57c?w=800",
    glbPath: "/models/avatar.glb",
    affiliateLinks: [
      { platform: "Ajio", url: "https://www.ajio.com/women-gowns", price: 8999 }
    ]
  }
];

const seedDB = async () => {
  try {
    await mongoose.connect(process.env.MONGO_URI);
    await Product.deleteMany({}); // Clears old data
    await Product.insertMany(sampleProducts);
    console.log("💎 Data Seeded! 5 Premium Outfits added to Vastra3D.");
    process.exit();
  } catch (err) {
    console.error(err);
    process.exit(1);
  }
};

seedDB();