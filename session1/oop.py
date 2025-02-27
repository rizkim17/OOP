class Car:
    def __init__(self, brand, model, year, driver):
        self.brand = brand
        self.model = model
        self.year = year
        self.driver = driver

    def display_car_info(self):
        print(f'Brand: {self.brand}, Model: {self.model}, Year: {self.year} , Supir: {self.driver}')

gojek1 = Car('Tesla', 'XXX', '3035', 'Tejo');
gojek2 = Car('Mitsubishi', 'Touring', '1999', 'Best');
gojek3 = Car('TimeX', 'Z10X', '0000', 'Gelar')

gojek1.display_car_info()
gojek2.display_car_info()
gojek3.display_car_info()
