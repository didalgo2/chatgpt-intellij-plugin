/*
 * Copyright (c) 2023 Mariusz Bernacki <consulting@didalgo.com>
 * SPDX-License-Identifier: Apache-2.0
 */
package com.didalgo.intellij.chatgpt.ui.prompt.context;

import com.didalgo.intellij.chatgpt.chat.InputContext;
import com.didalgo.intellij.chatgpt.chat.InputContextChangeEvent;
import com.didalgo.intellij.chatgpt.chat.PromptAttachment;
import com.didalgo.intellij.chatgpt.chat.InputContextListener;
import com.didalgo.intellij.chatgpt.event.ListenerList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultInputContext implements InputContext {
    private final List<PromptAttachment> entries;
    private final List<PromptAttachment> unmodifiableEntries;
    private final ListenerList<InputContextListener> listeners = ListenerList.of(InputContextListener.class);

    public DefaultInputContext() {
        this.entries = Collections.synchronizedList(new ArrayList<>());
        this.unmodifiableEntries = Collections.unmodifiableList(entries);
    }

    @Override
    public void addListener(InputContextListener listener) {
        listeners.addListener(listener);
    }

    @Override
    public void removeListener(InputContextListener listener) {
        listeners.removeListener(listener);
    }

    @Override
    public void addAttachment(PromptAttachment attachment) {
        entries.add(attachment);
        fireContextChanged();
    }

    @Override
    public void removeAttachment(PromptAttachment attachment) {
        entries.remove(attachment);
        fireContextChanged();
    }

    @Override
    public List<PromptAttachment> getAttachments() {
        return unmodifiableEntries;
    }

    @Override
    public boolean isEmpty() {
        return getAttachments().isEmpty();
    }

    @Override
    public void clear() {
        entries.clear();
        fireContextChanged();
    }

    protected void fireContextChanged() {
        InputContextChangeEvent event = new InputContextChangeEvent(this);
        listeners.fire().contextChanged(event);
    }
}