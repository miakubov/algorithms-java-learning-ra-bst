package ru.t1.education;

public class BinarySearchTree<KEY extends Comparable<KEY>, VALUE> {
    // Класс узла
    private class NodeBST {
        KEY key; //Ключ
        VALUE value; //Значение
        NodeBST left, right; //Дочерние узлы

        NodeBST(KEY key, VALUE value) {
            this.key = key;
            this.value = value;
        }
    }
    // Создаём корень дерева
    private NodeBST root;

    // Пытаемся получить данные по ключу поиском в глубину
    public VALUE get(KEY key) {
        // Начинаем поиск с корня
        NodeBST node = root;
        while (node!= null) {
            // Передаём в пременную cmp разницу ключей
            int cmp = key.compareTo(node.key);
            // Мы нашли нужный узел
            if (cmp == 0)
                return node.value;
            // Если разница меньше нуля переходим к левому потомку
            else if (cmp < 0)
                node = node.left;
            // Если разница больше нуля переходим к правому потомку
            else
                node = node.right;
        }
        return null; // не найдено
    }

    // Внешний метод вставки (ключ, значение)
    public void add(KEY key, VALUE value) {
        root = insert(root, key, value);
    }
    // Приватный метод вставки с уканием ноды 
    private NodeBST insert(NodeBST node, KEY key, VALUE value) {
        if (node == null) return new NodeBST(key, value);

        // Передаём в пременную cmp разницу ключей
        int cmp = key.compareTo(node.key);

        // Если разница меньше нуля переходим к левому потомку
        if (cmp < 0)
            node.left = insert(node.left, key, value);
        // Если разница больше нуля переходим к правому потомку
        else if (cmp > 0)
            node.right = insert(node.right, key, value);
        else
            node.value = value; // если ключ есть — обновляем значение
        return node;
    }

    // Обёртка для возвращаемого удаленного значения
    private static class Wrapper<V> {
        V value;
    }

    // Внешний метод удаления
    public VALUE remove(KEY key) {
        Wrapper<VALUE> removedValue = new Wrapper<>();
        root = remove(root, key, removedValue);
        return removedValue.value; // null если не найден
    }

    // Рекурсивный метод удаления узла с ключом key из поддерева
    private NodeBST remove(NodeBST node, KEY key, Wrapper<VALUE> removedValue) {
        if (node == null) {
            // Ключ не найден — возвращаем null
            removedValue.value = null;
            return null;
        }
        // Передаём в пременную cmp разницу ключей
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            // Ключ меньше текущего — ищем в левом поддереве
            node.left = remove(node.left, key, removedValue);
        } else if (cmp > 0) {
            // Ключ больше — ищем в правом поддереве
            node.right = remove(node.right, key, removedValue);
        } else {
            // Найден узел с ключом — удаляем его
            removedValue.value = node.value;

            // Случай 1: нет детей
            if (node.left == null && node.right == null) {
                return null;
            }

            // Случай 2: только одно поддерево
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            // Случай 3: два поддерева
            // Находим минимальный узел в правом поддереве
            NodeBST minRight = findMin(node.right);
            // Копируем ключ и значение минимального узла в текущий узел
            node.key = minRight.key;
            node.value = minRight.value;
            // Удаляем минимальный узел из правого поддерева
            node.right = remove(node.right, minRight.key, new Wrapper<>());
        }
        return node;
    }

    // Поиск минимального узла в дереве
    private NodeBST findMin(NodeBST node) {
        while (node.left!= null) {
            node = node.left;
        }
        return node;
    }
}
