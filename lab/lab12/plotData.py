import matplotlib.pyplot as plt

def read_And_Plot_Data(file):
    f = open(file, "r")
    lines = f.readlines()[3:]
    
    second = []
    arraySize = []
    
    for line in lines:
        line = line.split(" ");
        arraySize.append(line[0])
        second.append(line[2])
    
    f.close()

    return arraySize, second


def main():
    arraySize1, second1 = read_And_Plot_Data("insert.dat")
    arraySize2, second2 = read_And_Plot_Data("select.dat")
    arraySize3, second3 = read_And_Plot_Data("merge.dat")
    arraySize4, second4 = read_And_Plot_Data("quick.dat")
    
    plt.plot(arraySize1, second1, label = "insert sort", linewidth = 3)
    plt.plot(arraySize2, second2, label = "select sort", linewidth = 3)
    plt.plot(arraySize3, second3, label = "merge sort", linewidth = 3)
    plt.plot(arraySize4, second4, label = "quick sort", linewidth = 3, color = "yellow")
    
    plt.xlabel("Array size")
    plt.ylabel("Millisecond")
    plt.legend()
    plt.show()

main()
