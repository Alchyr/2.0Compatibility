package VersionTwoCompatiblity;

import org.clapper.util.classutil.ClassFilter;
import org.clapper.util.classutil.ClassFinder;
import org.clapper.util.classutil.ClassInfo;

public class PackageFilter implements ClassFilter {
    private String[] includedPackages;

    public PackageFilter(String[] includedPackages) {
        this.includedPackages = includedPackages;
    }

    public boolean accept(ClassInfo classInfo, ClassFinder classFinder) {
        for (String s : includedPackages)
            if (classInfo.getClassName().startsWith(s))
                return true;
        return false;
    }
}
