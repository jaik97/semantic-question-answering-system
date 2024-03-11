public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        //TODO Task 1.1
        this.doubElements = _elements;
    }

    public double getElementatIndex(int _index) {
        //TODO Task 1.2
        int length = getVectorSize();
        if(_index >= length)
            return -1.0;
        else
            return doubElements[_index];
    }

    public void setElementatIndex(double _value, int _index) {
        //TODO Task 1.3
        try {
            if (_index >= 0 || _index < this.getVectorSize())
                this.doubElements[_index] = _value;
        }
        catch (ArrayIndexOutOfBoundsException e){
            this.doubElements[this.getVectorSize()-1] = _value;
        }
    }

    public double[] getAllElements() {
        //TODO Task 1.4
        return this.doubElements;
    }

    public int getVectorSize() {
        //TODO Task 1.5
        return this.doubElements.length;
    }

    public Vector reSize(int _size) {
        //TODO Task 1.6
        int length = this.getVectorSize();

        if(_size <= 0 || _size == length){
            return this;
        }
        else if(_size < length){
            double[] re_arr = new double[_size];
            for(int i=0; i<_size; i++){
                re_arr[i] = this.doubElements[i];
            }
            Vector temp = new Vector(re_arr);
            return temp;
        }
        else{
            double[] re_arr = new double[_size-length +length];
            for(int i=0; i<re_arr.length; i++){
                if(i<length)
                    re_arr[i] = this.doubElements[i];
                else
                    re_arr[i] = -1.0;
            }
            Vector temp = new Vector(re_arr);
            return temp;
        }
    }

    public Vector add(Vector _v) {
        //TODO Task 1.7
        double[] add_arr;
        int u_length = this.getVectorSize();
        int v_length = _v.getVectorSize();
        if(v_length > u_length) {
            Vector temp_u = reSize(v_length);
            add_arr = new double[temp_u.getVectorSize()];
            for(int j = 0; j< temp_u.getVectorSize(); j++){
                add_arr[j] = temp_u.doubElements[j]+_v.doubElements[j];
            }
        }
        else if(v_length < u_length){
            Vector temp_v = _v.reSize(u_length);
            add_arr = new double[temp_v.getVectorSize()];
            for(int j = 0; j< temp_v.getVectorSize(); j++){
                add_arr[j] = this.doubElements[j]+temp_v.doubElements[j];
            }
        }
        else {
            add_arr = new double[u_length];
            for (int j = 0; j < u_length; j++) {
                add_arr[j] = this.doubElements[j] + _v.doubElements[j];
            }
        }
        Vector uv_add = new Vector(add_arr);
        return uv_add;
    }

    public Vector subtraction(Vector _v) {
        //TODO Task 1.8
        double[] add_arr;
        int u_length = this.getVectorSize();
        int v_length = _v.getVectorSize();
        if(v_length > u_length) {
            Vector temp_u = reSize(v_length);
            add_arr = new double[temp_u.getVectorSize()];
            for(int j = 0; j< temp_u.getVectorSize(); j++){
                add_arr[j] = temp_u.doubElements[j] - _v.doubElements[j];
            }
        }
        else if(v_length < u_length){
            Vector temp_v = _v.reSize(u_length);
            add_arr = new double[temp_v.getVectorSize()];
            for(int j = 0; j< temp_v.getVectorSize(); j++){
                add_arr[j] = this.doubElements[j] - temp_v.doubElements[j];
            }
        }
        else {
            add_arr = new double[u_length];
            for (int j = 0; j < u_length; j++) {
                add_arr[j] = this.doubElements[j] - _v.doubElements[j];
            }
        }
        Vector uv_sub = new Vector(add_arr);
        return uv_sub;
    }

    public double dotProduct(Vector _v) {
        //TODO Task 1.9
        double dotProd = 0.0;
        int u_length = this.getVectorSize();
        int v_length = _v.getVectorSize();
        if(v_length > u_length) {
            Vector temp_u = reSize(v_length);

            for(int j = 0; j< temp_u.getVectorSize(); j++){
                dotProd += temp_u.doubElements[j] * _v.doubElements[j];
            }
        }
        else if(v_length < u_length){
            Vector temp_v = _v.reSize(u_length);
            for(int j = 0; j< temp_v.getVectorSize(); j++){
                dotProd += this.doubElements[j] * temp_v.doubElements[j];
            }
        }
        else {

            for (int j = 0; j < u_length; j++) {
                dotProd += this.doubElements[j] * _v.doubElements[j];
            }
        }

        return dotProd;
    }

    public double cosineSimilarity(Vector _v) {
        //TODO Task 1.10
        double cs = 0.0;
        int u_length = this.getVectorSize();
        int v_length = _v.getVectorSize();
        if(v_length > u_length) {
            Vector temp_u = reSize(v_length);
            cs = this.dotProduct(_v)/(Math.sqrt(temp_u.dotProduct(temp_u))*(Math.sqrt(_v.dotProduct(_v))));

        }
        else if(v_length < u_length){
            Vector temp_v = _v.reSize(u_length);
            cs = this.dotProduct(_v)/(Math.sqrt(this.dotProduct(this))*(Math.sqrt(temp_v.dotProduct(temp_v))));

        }
        else {
            cs = this.dotProduct(_v)/(Math.sqrt(this.dotProduct(this))*(Math.sqrt(_v.dotProduct(_v))));
        }
        return cs;
    }

    @Override
    public boolean equals(Object _obj) {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;
        //TODO Task 1.11
        if(this.getVectorSize() != v.getVectorSize()) {
            boolEquals = false;
            return boolEquals;
        }
        else{
            for(int i = 0; i<this.getVectorSize(); i++) {
                if (this.doubElements[i] == v.doubElements[i]) {
                    ;
                } else {
                    boolEquals = false;
                    break;
                }
            }
            return boolEquals;
        }
    }

    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}
