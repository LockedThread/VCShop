package fr.galaxyoyo.spigot.nbtapi;

import com.sun.istack.internal.Nullable;
import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Set;

import static fr.galaxyoyo.spigot.nbtapi.ReflectionUtils.*;

public class TagCompound extends HashMap<String, Object> {
    private final Object owner;

    public TagCompound() {
        this(null);
    }

    public TagCompound(@Nullable Object owner) {
        this.owner = owner;
    }

    public static TagCompound fromNMS(Object nms) {
        return fromNMS(nms, null);
    }

    protected static TagCompound fromNMS(Object o, @Nullable Object owner) {
        if ( o == null )
            return new TagCompound(owner);

        Validate.isTrue(o.getClass().getSimpleName().equalsIgnoreCase("NBTTagCompound"), "Only a NBTTagCompound can be transformed into a TagCompound");
        TagCompound tag = new TagCompound(owner);
        Set<String> keys = invokeNMSMethod("c", o);
        for (String key : keys) {
            Object base = invokeNMSMethod("get", o, new Class<?>[]{ String.class }, key);
            Object data;
            switch (base.getClass().getSimpleName()) {
                case "NBTTagEnd":
                    data = null;
                    break;
                case "NBTTagCompound":
                    data = fromNMS(base);
                    break;
                case "NBTTagList":
                    data = TagList.fromNMS(base);
                    break;
                default:
                    data = getNMSField(base, "data");
                    break;
            }
            tag.put(key, data);
        }

        return tag;
    }

    public void setInt(String key, int value) {
        put(key, value);
    }

    public void setList(String key, TagList value) {
        put(key, value);
    }

    @Override
    public Object put(String key, Object value) {
        Object ret = super.put(key, value);
        if ( owner instanceof ItemStack ) ItemStackUtils.setTagCompound((ItemStack) owner, this);

        return ret;
    }

    @Override
    public Object get(Object key) {
        return super.get(key);
    }

    public int getInt(String key) {
        return (int) get(key);
    }

    TagList getList(String key) {
        return (TagList) get(key);
    }

    Object convertToNMS() {
        Object tag = newNMS("NBTTagCompound");
        forEach((key, value) -> {
            if ( value == null )
                invokeNMSMethod("set", tag, new Class<?>[]{ String.class, getNMSClass("NBTBase") }, key, newNMS("NBTTagEnd"));
            else if ( value instanceof String )
                invokeNMSMethod("setString", tag, new Class<?>[]{ String.class, String.class }, key, value);
            else if ( value instanceof Byte )
                invokeNMSMethod("setByte", tag, new Class<?>[]{ String.class, byte.class }, key, value);
            else if ( value instanceof Short )
                invokeNMSMethod("setShort", tag, new Class<?>[]{ String.class, short.class }, key, value);
            else if ( value instanceof Float )
                invokeNMSMethod("setFloat", tag, new Class<?>[]{ String.class, float.class }, key, value);
            else if ( value instanceof Double )
                invokeNMSMethod("setDouble", tag, new Class<?>[]{ String.class, double.class }, key, value);
            else if ( value instanceof Integer )
                invokeNMSMethod("setInt", tag, new Class<?>[]{ String.class, int.class }, key, value);
            else if ( value instanceof Long )
                invokeNMSMethod("setLong", tag, new Class<?>[]{ String.class, long.class }, key, value);
            else if ( value instanceof int[] )
                invokeNMSMethod("setIntArray", tag, new Class<?>[]{ String.class, int[].class }, key, value);
            else if ( value instanceof byte[] )
                invokeNMSMethod("setByteArray", tag, new Class<?>[]{ String.class, byte[].class }, key, value);
            else if ( value instanceof TagCompound )
                invokeNMSMethod("set", tag, new Class<?>[]{ String.class, getNMSClass("NBTBase") }, key, ((TagCompound) value).convertToNMS());
            else if ( value instanceof TagList )
                invokeNMSMethod("set", tag, new Class<?>[]{ String.class, getNMSClass("NBTBase") }, key, ((TagList) value).convertToNMS());
        });
        return tag;
    }
}