def parse_log(log_line):
    """
    Mengekstrak informasi dari baris log dengan format:
    "2025-05-01 21:45:30 - INFO - User 'andi' logged in from 192.168.1.100"
    
    Args:
        log_line (str): Baris log yang akan dianalisis
        
    Returns:
        dict: Dictionary berisi informasi yang diekstrak
    """
    parts = log_line.split(' - ')
    
    datetime_part = parts[0]
    time = datetime_part.split()[1] 
    level = parts[1]
    user_ip_part = parts[2]
    username_start = user_ip_part.find("'") + 1
    username_end = user_ip_part.find("'", username_start)
    username = user_ip_part[username_start:username_end]
    ip_address = user_ip_part.split()[-1]
    result = {
        "waktu": time,
        "level": level,
        "username": username,
        "ip_address": ip_address
    }
    
    return result


if __name__ == "__main__":
    log_line = "2025-05-01 21:45:30 - INFO - User 'andi' logged in from 192.168.1.100"
    hasil = parse_log(log_line)
    
    print("Hasil ekstraksi log:")
    print(f"Waktu: {hasil['waktu']}")
    print(f"Level: {hasil['level']}")
    print(f"Username: {hasil['username']}")
    print(f"Alamat IP: {hasil['ip_address']}")