import random

def generate_account_string():
    entries = []
    
    for i in range(1, 101):
        account_number = f"account{i}"
        account_balance = round(random.uniform(100.0, 90000.99), 2)
        entries.append(f"{account_number}: {account_balance:.2f}")
    
    return ", ".join(entries)

if __name__ == "__main__":
    result = generate_account_string()
    print(result)
