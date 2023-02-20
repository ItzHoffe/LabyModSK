package dk.hoffe.labymodsk.Classes;

import org.bukkit.Bukkit;

import java.util.Arrays;

public class LabyVersion {
    private int major = 0;
    private int minor = 0;
    private int patch;

    public LabyVersion(String version) {
        System.out.println(version);
        String[] semver = version.split("\\.");
        System.out.println(Arrays.toString(semver));
        try {
            major = Integer.parseInt(semver[0]);
            minor = Integer.parseInt(semver[1]);
            patch = Integer.parseInt(semver[2]);
        } catch (NumberFormatException e) {

        }
    }

    @Override
    public String toString() {
        return "LabyVersion{" +
                "major=" + major +
                ", minor=" + minor +
                ", patch=" + patch +
                '}';
    }
}
