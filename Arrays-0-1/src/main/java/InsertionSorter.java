public class InsertionSorter implements Sorter{
    @Override
    public int[] sort(int[] arr){
        if(arr.length == 0){
            return copyArray(arr);
        }
        if (arr == null){
            throw new NullPointerException("Array is null");
        }
        int[] copyArr = copyArray(arr);
        copyArr = insertion(copyArr);
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

    int[] insertion(int[] arr){
        int N = arr.length;

        for (int j = 2; j < N; j++) {
            int key = arr[j];
            int i = j - 1;
            while(i >= 0 && arr[i] > key){
                arr[i+1] = arr[i];
                i = i - 1;
            }
            arr[i+1] = key;
        }
        return arr;
    }
}
