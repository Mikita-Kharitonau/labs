import random
import collections


DefaultKey = collections.namedtuple('DefaultKey', 'p g bits_number')
PrivateKey = collections.namedtuple('PrivateKey', DefaultKey._fields + ('h', ))
PublicKey = collections.namedtuple('PublicKey', DefaultKey._fields + ('x', ))


def gcd(a, b):
    while b != 0:
        c = a % b
        a = b
        b = c
    return a


def SS(num, confidence):
    for i in range(confidence):
        a = random.randint(1, num - 1)
        if gcd(a, num) > 1:
            return False
        if not jacobi(a, num) % num == pow(a, (num - 1) // 2, num):
            return False
    return True


def jacobi(a, n):
    if a == 0:
        if n == 1:
            return 1
        else:
            return 0
    elif a == -1:
        if n % 2 == 0:
            return 1
        else:
            return -1
    elif a == 1:
        return 1
    elif a == 2:
        if n % 8 == 1 or n % 8 == 7:
            return 1
        elif n % 8 == 3 or n % 8 == 5:
            return -1
    elif a >= n:
        return jacobi(a % n, n)
    elif a % 2 == 0:
        return jacobi(2, n) * jacobi(a // 2, n)
    else:
        if a % 4 == 3 and n % 4 == 3:
            return -1 * jacobi(n, a)
        else:
            return jacobi(n, a)


def find_primitive_root(p):
    if p == 2:
        return 1
    p1 = 2
    p2 = (p - 1) // p1
    while 1:
        g = random.randint(2, p - 1)
        if not pow(g, (p - 1) // p1, p) == 1:
            if not pow(g, (p - 1) // p2, p) == 1:
                return g


def rand_by_bits(bits_number):
    return random.randint(2 ** (bits_number - 2), 2 ** (bits_number - 1))


def find_prime(bits_number, confidence):
    while True:
        p = rand_by_bits(bits_number)
        while p % 2 == 0:
            p = rand_by_bits(bits_number)
        while not SS(p, confidence):
            p = rand_by_bits(bits_number)
            while p % 2 == 0:
                p = rand_by_bits(bits_number)

        p = p * 2 + 1
        if SS(p, confidence):
            return p


def encode(text, bits_number):
    byte_array = bytearray(text, "utf-16")
    z = []
    k = bits_number // 8
    j = -1 * k
    for i in range(len(byte_array)):
        if i % k == 0:
            j += k
            z.append(0)
        z[j // k] += byte_array[i] * (2 ** (8 * (i % k)))

    return z


def decode(text, bits_number):
    bytes_array = []
    k = bits_number // 8

    for num in text:
        for i in range(k):
            temp = num
            for j in range(i + 1, k):
                temp = temp % (2 ** (8 * j))
            letter = temp // (2 ** (8 * i))
            bytes_array.append(letter)
            num = num - (letter * (2 ** (8 * i)))
    decodedText = bytearray(b for b in bytes_array).decode("utf-16")

    return decodedText


def generate_keys(bits_number=256, confidence=32):
    p = find_prime(bits_number, confidence)
    g = find_primitive_root(p)
    g = pow(g, 2, p)
    x = random.randint(1, (p - 1) // 2)
    h = pow(g, x, p)

    publicKey = PrivateKey(p, g, h, bits_number)
    privateKey = PublicKey(p, g, x, bits_number)

    return privateKey, publicKey


def encrypt(key, text):
    z = encode(text, key.bits_number)
    cipher_pairs = []
    for i in z:
        y = random.randint(0, key.p)
        c = pow(key.g, y, key.p)
        d = (i * pow(key.h, y, key.p)) % key.p
        cipher_pairs.append([c, d])

    encrypted = ""
    for pair in cipher_pairs:
        encrypted += f'{pair[0]} {pair[1]} '

    return encrypted


def decrypt(key, cipher):
    text = []

    cipher = cipher.split()
    if not len(cipher) % 2 == 0:
        raise ValueError("Malformed Cipher Text")
    for i in range(0, len(cipher), 2):
        c = int(cipher[i])
        d = int(cipher[i + 1])

        s = pow(c, key.x, key.p)
        plain = (d * pow(s, key.p - 2, key.p)) % key.p
        text.append(plain)
    decrypted = decode(text, key.bits_number)
    decrypted = "".join([ch for ch in decrypted if ch != "\x00"])
    return decrypted


if __name__ == '__main__':
    private, public = generate_keys()
    message = "Here we go"
    cipher = encrypt(public, message)
    plain = decrypt(private, cipher)
    print('private:', private)
    print('public:', public)
    print("encrypted", cipher)
    print("decrypted", plain)
