class Hewan:
    def bersuara(self):
        print("Hewan Bersuara")

class Kucing:
    def bersuara(self):  
        print("Kucing Bersuara: Kukkuruyukkkk")

class Anjing:
    def bersuara(self):  
        print("Anjing Bersuara: Tokekkkk!!!")

def suara_hewan(hewan):
    hewan.bersuara()

hewan_umum = Hewan ()
kucing = Kucing ()
anjing = Anjing()

suara_hewan(hewan_umum)
suara_hewan(kucing)
suara_hewan(anjing)