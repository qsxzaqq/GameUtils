package cc.i9mc.gameutils.utils.nms.book;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

/**
 * Book - 数据模型
 *
 * @author Zoyn
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private List<String> pages = Lists.newArrayList();

    public ItemStack getItem() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();
        meta.setTitle("");
        meta.setAuthor("");
        BookUtils.setPagesAsPage(meta, pages);
        book.setItemMeta(meta);
        return book;
    }
}
