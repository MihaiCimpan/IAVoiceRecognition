import re
import sys
import os
import shutil

path = sys.argv[1]
emotion = sys.argv[2]
procent_antrenare = float(sys.argv[3])
procent_test = float(sys.argv[4])

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
		print(contor)
		print(nr_antrenare)
		print(nr_test)
		print('-----')
		for j in range(0, nr_antrenare):
			shutil.copy2(path + '\\' + lista_fisiere[j], new)
			os.remove(path + '\\' + lista_fisiere[j])

		for x in range(nr_antrenare + 1, nr_test + nr_antrenare - 1):
			shutil.copy2(path + '\\' + lista_fisiere[x], new2)
			os.remove(path + '\\' + lista_fisiere[x])



impartire_fisiere(path)
