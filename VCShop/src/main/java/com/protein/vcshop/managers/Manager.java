package com.protein.vcshop.managers;

import com.protein.vcshop.VCShop;

public abstract class Manager {

    public VCShop instance;

    public Manager(VCShop instance) {
        this.instance = instance;
    }
}
