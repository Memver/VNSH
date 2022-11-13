package Arrays02;

public class BubbleSorter {

    public int[] bubbleSorter(int[] arr){
        if(arr.length == 0){
            return copyArray(arr);
        }
        if (arr == null){
            throw new NullPointerException("Array is null");
        }
        int[] copyArr = copyArray(arr);
        copyArr = vozrast(copyArr);
        return copyArr;
    }

    int[] copyArray(int[] arr){
        int N = arr.length;
        int[] copyArr = new int[N];
        for (int i = 0; i < N; i++) {
            copyArr[i] = arr[i];
        }
        return copyArr;
    }

    int[] vozrast(int[] arr){
        int sorted = 0;
        int j;

        // цикл сортировки массива
        while (true) {

            // Условие отсортированного массива
            for (int i = 0; i < arr.length - 1; i++) {
                if (arr[i + 1] >= arr[i]) {
                    sorted = 1;
                } else {
                    sorted = 0;
                    break;
                }
            }

            // Сортировка методом пузырька
            if (sorted == 0) {
                for (int i = 0; i < arr.length - 1; i = i + 1) {
                    if (arr[i + 1] < arr[i]) {
                        j = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = j;
                    }
                }

            } else {
                return arr;
            }
        }
    }
}