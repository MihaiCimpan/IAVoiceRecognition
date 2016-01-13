import re
import sys
import os
import shutil


def search_file(path, emotion):
	contor = 0
	lista_fisiere = []
	files = os.listdir(path)
	for item in files:
		if os.path.isfile(path + '\\' + item):
			if item[5].upper() == emotion.upper():
				lista_fisiere.append(item)
	contor = len(lista_fisiere)
	return contor, lista_fisiere	

def impartire_fisiere(path):
	emotii = ['N', 'J', 'S', 'F', 'A', 'B', 'D']
	new = path + '\\SA'
	print(new)
	if not os.path.exists(new):
		os.makedirs(new)

	new2 = path + '\\ST'
	print(new2)
	if not os.path.exists(new2):
		os.makedirs(new2)
	
	for i in emotii:
		contor, lista_fisiere = search_file(path, i)
		nr_antrenare = int(procent_antrenare/100*contor) + 1
		nr_test = int(procent_test/100*contor)
		print(nr_antrenare)
		print(nr_test)
		for j in range(0, nr_antrenare):
			shutil.copy2(path + '\\' + lista_fisiere[j], new)

		for x in range(nr_antrenare + 1, nr_test + nr_antrenare - 1):
			shutil.copy2(path + '\\' + lista_fisiere[x], new2)

if len(sys.argv) < 4:
	print("Prea putini parametrii. Scriptul se apeleaza in felul urmator: separare.py path_director_muzica procent_antrenare procent_test")
else:
	path = sys.argv[1]
	procent_antrenare = float(sys.argv[2])
	procent_test = float(sys.argv[3])
	impartire_fisiere(path)
