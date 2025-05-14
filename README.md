💳 ATM Simulation in Java with File Handling
This is a simple Java-based ATM simulation that supports user authentication, balance checking, deposits, withdrawals, and transaction history logging. All data, including accounts and transactions, are stored persistently using text files.
🚀 Features
🔐 User Authentication (via user ID and PIN)

💰 Balance Inquiry

➕ Deposit Money

➖ Withdraw Money

🧾 View Transaction History

🧠 File I/O for persistent storage (accounts and transaction history)
🧑‍💻 How It Works
On launch, the program loads account data from accounts.txt.

Users must enter a valid User ID and PIN to access their account.

Once logged in, users can perform transactions through a simple menu interface.

All transactions are recorded both in memory and in a transactions_<userId>.txt file.

On exit, all account balances are saved back to accounts.txt.

🛠️ Prerequisites
Java JDK 8 or above

Any Java IDE (e.g., IntelliJ, Eclipse) or command-line terminal
📄 File Descriptions
File	Description
ATMWithFile.java	Main program logic and user interface
accounts.txt	Stores userId, pin, and balance
transactions_<userId>.txt	Transaction history for each user

🔐 Security Notes
PINs are stored in plain text for simplicity; in a real application, they should be encrypted.

No concurrency or multi-threading is implemented.

📌 Future Improvements
Encrypt PINs and secure file access

Add new user registration feature

Implement GUI using JavaFX or Swing

Add concurrency handling for multi-user support

🧑‍🎓 Author
Your Name (You can add your contact or GitHub link here)

📜 License
This project is for educational purposes and does not carry any license.


