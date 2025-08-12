// MongoDB initialization script
// This script runs when the MongoDB container starts for the first time

// Switch to the site_management database
db = db.getSiblingDB('site_management');

// Create collections with proper indexes
db.createCollection('users');
db.createCollection('announcements');
db.createCollection('payments');
db.createCollection('issues');
db.createCollection('files');

// Create indexes for better performance
db.users.createIndex({ "email": 1 }, { unique: true });
db.users.createIndex({ "apartmentNumber": 1 });
db.users.createIndex({ "buildingNumber": 1 });
db.users.createIndex({ "roles": 1 });

db.announcements.createIndex({ "isActive": 1 });
db.announcements.createIndex({ "publishDate": 1 });
db.announcements.createIndex({ "targetAudience": 1 });

db.payments.createIndex({ "userId": 1 });
db.payments.createIndex({ "dueDate": 1 });
db.payments.createIndex({ "status": 1 });
db.payments.createIndex({ "dueMonth": 1 });

db.issues.createIndex({ "reporterId": 1 });
db.issues.createIndex({ "status": 1 });
db.issues.createIndex({ "priority": 1 });
db.issues.createIndex({ "issueType": 1 });

db.files.createIndex({ "uploadedBy": 1 });
db.files.createIndex({ "relatedEntityType": 1 });
db.files.createIndex({ "relatedEntityId": 1 });

// Create initial admin user
// Password: admin123 (BCrypt hash)
const adminUser = {
    firstName: "Admin",
    lastName: "User",
    email: "admin@smartshopai.com",
    phoneNumber: "+905551234567",
    password: "$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi",
    apartmentNumber: "ADMIN",
    buildingNumber: "ADMIN",
    floorNumber: 0,
    roles: ["ADMIN"],
    enabled: true,
    accountNonExpired: true,
    accountNonLocked: true,
    credentialsNonExpired: true,
    createdAt: new Date(),
    updatedAt: new Date()
};

// Insert admin user if it doesn't exist
const existingAdmin = db.users.findOne({ "email": "admin@smartshopai.com" });
if (!existingAdmin) {
    db.users.insertOne(adminUser);
    print("Admin user created successfully");
} else {
    print("Admin user already exists");
}

// Create initial announcement
const welcomeAnnouncement = {
    title: "Hoş Geldiniz!",
    content: "Site yönetim sistemine hoş geldiniz. Bu sistem ile duyuruları takip edebilir, aidat ödemelerinizi görüntüleyebilir ve arıza bildirimlerinde bulunabilirsiniz.",
    authorId: adminUser._id,
    authorName: "Admin User",
    isActive: true,
    isImportant: true,
    publishDate: new Date(),
    targetAudience: "ALL",
    createdAt: new Date(),
    updatedAt: new Date()
};

// Insert welcome announcement if it doesn't exist
const existingAnnouncement = db.announcements.findOne({ "title": "Hoş Geldiniz!" });
if (!existingAnnouncement) {
    db.announcements.insertOne(welcomeAnnouncement);
    print("Welcome announcement created successfully");
} else {
    print("Welcome announcement already exists");
}

print("MongoDB initialization completed successfully!");
