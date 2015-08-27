package com.pkayjava.editor.controller;


import com.puremvc.patterns.command.MacroCommand;

/**
 * Created by socheatkhauv on 8/26/15.
 */
public class StartupCommand extends MacroCommand {
    @Override
    protected void initializeMacroCommand() {
        super.initializeMacroCommand();
        addSubCommand(BootstrapProxyCommand.class);
        addSubCommand(BootstrapViewCommand.class);
        addSubCommand(BootstrapCommand.class);
        addSubCommand(BootstrapPlugins.class);
    }
}
