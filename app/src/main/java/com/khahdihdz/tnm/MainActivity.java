    void deleteProduct(Product p){
        new AlertDialog.Builder(this).setTitle("Xóa sản phẩm").setMessage("Bạn có chắc muốn xóa “"+p.name+"”?\\n\\nTồn kho hiện tại: "+p.stock+" "+p.unit+".")
            .setNegativeButton("Hủy",null).setPositiveButton("Xóa",(d,w)->{products.remove(p);save();build("products");toast("Đã xóa sản phẩm");}).show();